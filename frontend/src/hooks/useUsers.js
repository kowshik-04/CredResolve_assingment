import { useState } from "react";
import { getUsers } from "../api/users.api";

export const useUsers = () => {
  const [users, setUsers] = useState([]);

  const loadUsers = async () => {
    const data = await getUsers();
    setUsers(data);
  };

  return { users, loadUsers };
};
