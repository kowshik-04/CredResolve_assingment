import { useState } from "react";
import { getGroupsByUser } from "../api/groups.api";

export const useGroups = () => {
  const [groups, setGroups] = useState([]);

  const loadGroups = async (userId) => {
    const data = await getGroupsByUser(userId);
    setGroups(data || []);
  };

  return { groups, loadGroups };
};
