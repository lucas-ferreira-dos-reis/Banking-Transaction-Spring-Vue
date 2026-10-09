<template>
  <div class="card shadow-sm p-4">
    <h4 class="mb-3">Nova Transferência</h4>

    <div v-if="successMessage" class="alert alert-success" role="alert">
      {{ successMessage }}
    </div>

    <form @submit.prevent="handleSubmit">
      <div class="mb-3">
        <label class="form-label">Conta de Origem (10 dígitos):</label>
        <input
          type="text"
          class="form-control"
          :class="{ 'is-invalid': errors.sourceAccount }"
          v-model="form.sourceAccount"
          maxlength="10"
          placeholder="Ex: 1234567890"
          required
        />
      </div>
      <div class="invalid-feedback d-block" v-if="errors.sourceAccount">
        {{ errors.sourceAccount }}
      </div>

      <div class="mb-3">
        <label class="form-label">Conta de Destino (10 dígitos):</label>
        <input
          type="text"
          class="form-control"
          :class="{ 'is-invalid': errors.destinationAccount }"
          v-model="form.destinationAccount"
          maxlength="10"
          placeholder="Ex: 0987654321"
          required
        />
      </div>
      <div class="invalid-feedback d-block" v-if="errors.destinationAccount">
        {{ errors.destinationAccount }}
      </div>

      <div class="mb-3">
        <label class="form-label">Valor da Transferência (R$):</label>
        <input
          type="number"
          step="0.01"
          class="form-control"
          :class="{ 'is-invalid': errors.transferAmount }"
          v-model="form.transferAmount"
          placeholder="0.00"
          required
        />
      </div>
      <div class="invalid-feedback d-block" v-if="errors.transferAmount">
        {{ errors.transferAmount }}
      </div>

      <div class="mb-3">
        <label class="form-label">Data da Transferência:</label>
        <input
          type="date"
          class="form-control"
          :class="{ 'is-invalid': errors.scheduleDate }"
          v-model="form.scheduleDate"
          :min="minScheduleDate"
          :max="maxScheduleDate"
          required
        />
      </div>
      <div class="invalid-feedback d-block" v-if="errors.scheduleDate">
        {{ errors.scheduleDate }}
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
      errors: {},
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
      this.errors = {};

      if (!accountRegex.test(this.form.sourceAccount)) {
        this.errors.sourceAccount =
          "A conta de origem deve conter exatamente 10 dígitos numéricos.";
      }

      if (!accountRegex.test(this.form.destinationAccount)) {
        this.errors.destinationAccount =
          "A conta de destino deve conter exatamente 10 dígitos numéricos.";
      }

      if (
        this.form.sourceAccount &&
        this.form.destinationAccount &&
        this.form.sourceAccount === this.form.destinationAccount
      ) {
        this.errors.destinationAccount =
          "A conta de origem e destino não podem ser iguais.";
      }

      if (
        this.form.transferAmount === "" ||
        !Number.isFinite(Number(this.form.transferAmount)) ||
        Number(this.form.transferAmount) <= 0
      ) {
        this.errors.transferAmount =
          "O valor da transferência deve ser maior que zero.";
      }

      if (!this.form.scheduleDate) {
        this.errors.scheduleDate = "A data da transferência é obrigatória.";
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
          this.errors.scheduleDate =
            "A data da transferência não pode estar no passado.";
        } else if (scheduleDate > maxDate) {
          this.errors.scheduleDate =
            "A data da transferência está fora das faixas estipuladas pela tabela.";
        }
      }

      return Object.keys(this.errors).length === 0;
    },

    handleSubmit() {
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
