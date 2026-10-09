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
          :min="minScheduleDate"
          :max="maxScheduleDate"
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
  computed: {
    minScheduleDate() {
      return this.formatDate(new Date());
    },

    maxScheduleDate() {
      const date = new Date();
      date.setDate(date.getDate() + 50);
      return this.formatDate(date);
    },
  },
  methods: {
    formatDate(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");

      return `${year}-${month}-${day}`;
    },
    validateForm() {
      const accountRegex = /^\d{10}$/;
      const hasError = false;

      if (!accountRegex.test(this.form.sourceAccount)) {
        this.errorMessage =
          "A conta de origem deve conter exatamente 10 dígitos numéricos.";
        hasError = true;
      }

      if (!accountRegex.test(this.form.destinationAccount)) {
        this.errorMessage =
          "A conta de destino deve conter exatamente 10 dígitos numéricos.";
        hasError = true;
      }

      if (this.form.sourceAccount === this.form.destinationAccount) {
        this.errorMessage = "A conta de origem e destino não podem ser iguais.";
        hasError = true;
      }

      if (Number(this.form.transferAmount) <= 0) {
        this.errorMessage = "O valor da transferência deve ser maior que zero.";
        hasError = true;
      }

      if (!this.form.scheduleDate) {
        this.errorMessage = "A data da transferência é obrigatória.";
        hasError = true;
      } else {
        const [year, month, day] = this.form.scheduleDate
          .split("-")
          .map(Number);

        const scheduleDate = new Date(year, month - 1, day);
        scheduleDate.setHours(0, 0, 0, 0);

        const today = new Date();
        today.setHours(0, 0, 0, 0);

        const maxDate = new Date(today);
        maxDate.setDate(maxDate.getDate() + 50);

        if (scheduleDate < today) {
          this.errorMessage =
            "A data da transferência não pode estar no passado.";
          hasError = true;
        } else if (scheduleDate > maxDate) {
          this.errorMessage =
            "A data da transferência está fora das faixas estipuladas pela tabela.";
          hasError = true;
        }
      }

      return !hasError;
    },

    handleSubmit() {
      this.errorMessage = "";
      this.successMessage = "";

      if (!this.validateForm()) {
        return;
      }

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
