import axios from "./axios";

export const addExpense = async (payload) => {
  const res = await axios.post("/expenses", payload);
  return res.data;
};
