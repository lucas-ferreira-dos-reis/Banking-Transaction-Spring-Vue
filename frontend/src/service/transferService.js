import $ from "jquery";

const TRANSFER_URL = "http://localhost:8080/api/transfers";

export const transferService = {
  getAllTransfers(successCallback, errorCallback) {
    $.ajax({
      url: TRANSFER_URL,
      method: "GET",
      success: successCallback,
      error: errorCallback,
    });
  },

  createTransfer(transferData, successCallback, errorCallback) {
    $.ajax({
      url: TRANSFER_URL,
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify(transferData),
      success: successCallback,
      error: errorCallback,
    });
  },
};
