import { useEffect, useState, useContext } from "react";
import { getUsers } from "../api/users.api";
import { UserContext } from "../context/UserContext";

export default function SelectUser() {
  const { setUser } = useContext(UserContext);
  const [users, setUsers] = useState([]);

  useEffect(() => {
    getUsers()
      .then((res) => {
        console.log("USERS:", res.data); // 🔥 DEBUG
        setUsers(res.data);              // 🔥 IMPORTANT
      })
      .catch((err) => console.error(err));
  }, []);

  return (
    <div style={{ padding: "60px", maxWidth: 500 }}>
      <h2>Select User</h2>

      {users.length === 0 && <p>No users found</p>}

      {users.map((u) => (
        <button
          key={u.id}
          onClick={() => setUser(u)}
          style={{
            display: "block",
            width: "100%",
            margin: "12px 0",
            padding: "14px",
            borderRadius: "12px",
            border: "1px solid #ddd",
            background: "white",
            fontSize: "16px",
            cursor: "pointer",
          }}
        >
          {u.name}
        </button>
      ))}
    </div>
  );
}
