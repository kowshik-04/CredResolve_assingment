import axios from "./axios";

export const getUsers = () => {
  return axios.get("/users");
};


export const createUser = async (user) => {
  const res = await axios.post("/users", user);
  return res.data;
};
