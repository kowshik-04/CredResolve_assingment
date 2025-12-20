export default function UserSelector({ users, onSelect }) {
  return (
    <select
      className="w-full p-3 border rounded-xl shadow"
      onChange={(e) => onSelect(Number(e.target.value))}
    >
      <option>Select User</option>
      {users.map((u) => (
        <option key={u.id} value={u.id}>
          {u.name}
        </option>
      ))}
    </select>
  );
}
