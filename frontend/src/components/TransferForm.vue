<template>
  <div class="card shadow-sm p-4">
    <h4 class="mb-3">Nova Transferência</h4>

    <div v-if="errorMessage" class="alert alert-danger" role="alert">
      {{ errorMessage }}
    </div>
    <div v-if="successMessage" class="alert alert-success" role="alert">
      {{ successMessage }}
    </div>

    <form @submit.prevent="handleSubmit">
      <div class="mb-3">
        <label class="form-label">Conta de Origem (10 dígitos):</label>
        <input
          type="text"
          class="form-control"
          v-model="form.sourceAccount"
          maxlength="10"
          placeholder="Ex: 1234567890"
          required
        />
      </div>

      <div class="mb-3">
        <label class="form-label">Conta de Destino (10 dígitos):</label>
        <input
          type="text"
          class="form-control"
          v-model="form.destinationAccount"
          maxlength="10"
          placeholder="Ex: 0987654321"
          required
        />
      </div>

      <div class="mb-3">
        <label class="form-label">Valor da Transferência (R$):</label>
        <input
          type="number"
          step="0.01"
          class="form-control"
          v-model="form.transferAmount"
          placeholder="0.00"
          required
        />
      </div>

      <div class="mb-3">
        <label class="form-label">Data da Transferência:</label>
        <input
          type="date"
          class="form-control"
          v-model="form.scheduleDate"
          required
        />
      </div>

      <button type="submit" class="btn btn-primary w-100">
        Agendar Transferência
      </button>
    </form>
  </div>
</template>

<script>
import { transferService } from "@/service/transferService.js";

export default {
  name: "TransferForm",
  data() {
    return {
      form: {
        sourceAccount: "",
        destinationAccount: "",
        transferAmount: "",
        scheduleDate: "",
      },
      errorMessage: "",
      successMessage: "",
    };
  },
  methods: {
    handleSubmit() {
      this.errorMessage = "";
      this.successMessage = "";

      transferService.createTransfer(
        this.form,
        () => {
          this.successMessage = "Transferência agendada com sucesso!";

          this.form.sourceAccount = "";
          this.form.destinationAccount = "";
          this.form.transferAmount = "";
          this.form.scheduleDate = "";

          this.$emit("transfer-created");
        },
        (error) => {
          this.errorMessage =
            error.responseText || "Erro ao processar agendamento.";
        },
      );
    },
  },
};
</script>
