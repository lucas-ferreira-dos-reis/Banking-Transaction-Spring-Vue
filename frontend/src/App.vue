<template>
  <div class="container mt-4">
    <h2 class="mb-4 text-center">Sistema de Agendamento de Transferências</h2>

    <div class="row">
      <div class="col-md-6 mb-4">
        <TransferForm @transfer-created="fetchTransfers" />
      </div>

      <div class="col-md-6 mb-4">
        <TaxTable />
      </div>
    </div>

    <div class="row">
      <div class="col-12">
        <TransferList :transfers="transfers" />
      </div>
    </div>
  </div>
</template>

<script>
import TransferForm from "./components/TransferForm.vue";
import TaxTable from "./components/TaxTable.vue";
import TransferList from "./components/TransferList.vue";
import { transferService } from "./service/transferService.js";
import "bootstrap/dist/css/bootstrap.min.css";

export default {
  name: "App",
  components: {
    TransferForm,
    TaxTable,
    TransferList,
  },
  data() {
    return {
      transfers: [],
    };
  },
  mounted() {
    this.fetchTransfers();
  },
  methods: {
    fetchTransfers() {
      transferService.getAllTransfers(
        (response) => {
          this.transfers = response;
        },
        (error) => {
          console.error("Error on Transfer List search", error);
        },
      );
    },
  },
};
</script>
