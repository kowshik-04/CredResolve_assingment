import axios from "./axios";

export const getSettlements = async (groupId) => {
  const res = await axios.get(`/settlements/${groupId}`);
  return res.data;
};

export const settleUp = async (payload) => {
  const res = await axios.post("/settle", payload);
  return res.data;
};
