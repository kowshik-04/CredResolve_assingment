import { useContext, useEffect } from "react";
import { UserContext } from "../context/UserContext";
import { useGroups } from "../hooks/useGroups";
import GroupCard from "../components/GroupCard";
import { Link } from "react-router-dom";

export default function Groups() {
  const { user } = useContext(UserContext);
  const { groups, loadGroups } = useGroups();

  useEffect(() => {
    if (user) loadGroups(user.id);
  }, [user]);

  return (
    <div className="container space-y-4">
      <div className="flex justify-between">
        <h2 className="text-2xl font-bold">Your Groups</h2>
        <Link to="/create-group" className="btn-primary">
          + Create
        </Link>
      </div>

      <div className="grid gap-4">
        {groups.map((g) => (
          <GroupCard key={g.id} group={g} />
        ))}
      </div>
    </div>
  );
}
