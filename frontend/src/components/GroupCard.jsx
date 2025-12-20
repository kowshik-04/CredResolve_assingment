import { Link } from "react-router-dom";

export default function GroupCard({ group }) {
  return (
    <Link
      to={`/groups/${group.id}`}
      className="block bg-white rounded-xl p-4 shadow hover:shadow-lg transition"
    >
      <h3 className="text-lg font-semibold">{group.name}</h3>
      <p className="text-gray-500 text-sm">
        Created by {group.createdBy.name}
      </p>
    </Link>
  );
}
