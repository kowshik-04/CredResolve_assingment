import api from "./axios";

export const getGroupsByUser = async (userId) => {
  const res = await api.get(`/groups/user/${userId}`);
  return res.data;
};

export const createGroup = async ({ name, createdBy, memberIds = [] }) => {
  const res = await api.post("/groups", {
    name,
    createdBy,
    memberIds,
  });
  return res.data;
};
