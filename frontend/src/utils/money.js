export const toRupees = (paise) => (paise / 100).toFixed(2);

export const toPaise = (rupees) => Math.round(Number(rupees) * 100);
