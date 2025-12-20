import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { createGroup } from "../api/groups.api";
import { UserContext } from "../context/UserContext";

export default function CreateGroup() {
  const [name, setName] = useState("");
  const { user } = useContext(UserContext);
  const navigate = useNavigate();

  const submit = async () => {
    try {
      await createGroup({
        name,
        createdBy: user.id,
        memberIds: [user.id],
      });
      navigate("/");
    } catch {
      alert("Group name already exists");
    }
  };

  return (
    <div className="container max-w-md">
      <h1 className="text-2xl font-bold mb-4">Create Group</h1>

      <input
        className="input mb-4"
        placeholder="Unique Group Name"
        onChange={(e) => setName(e.target.value)}
      />

      <button className="btn-primary w-full" onClick={submit}>
        Create
      </button>
    </div>
  );
}
