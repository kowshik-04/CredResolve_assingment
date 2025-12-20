import api from "./axios";

/**
 * Get balances for a group
 * GET /balances/{groupId}
 */
export const getBalances = async (groupId) => {
  const res = await api.get(`/balances/${groupId}`);
  return res.data;
};

/**
 * Get simplified settlements for a group
 * GET /settlements/{groupId}
 */
export const getSettlementsByGroup = async (groupId) => {
  const res = await api.get(`/settlements/${groupId}`);
  return res.data;
};
