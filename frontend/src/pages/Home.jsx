import { useContext, useEffect, useState } from "react";
import { UserContext } from "../context/UserContext";
import { getGroupsByUser } from "../api/groups.api";

export default function Home() {
  const { user } = useContext(UserContext);
  const [groups, setGroups] = useState([]);

  // 🛑 VERY IMPORTANT GUARD
  if (!user) {
    return <h2>Loading user...</h2>;
  }

  useEffect(() => {
    getGroupsByUser(user.id)
      .then((res) => setGroups(res.data))
      .catch((err) => console.error(err));
  }, [user.id]);

  return (
    <div style={{ padding: 24 }}>
      <h2>Welcome, {user.name}</h2>

      {groups.length === 0 && <p>No groups yet</p>}

      {groups.map((g) => (
        <div key={g.id}>
          <strong>{g.name}</strong>
        </div>
      ))}
    </div>
  );
}
