<template>
  <div class="container mt-4">
    <h2 class="mb-4 text-center">Sistema de Agendamento de Transferências</h2>

    <div class="row">
      <div class="col-md-6 mb-4">
        <!-- Formulário -->
      </div>

      <div class="col-md-6 mb-4">
        <TaxTable />
      </div>
    </div>

    <div class="row">
      <div class="col-12">
        <!-- Lista de transferências -->
      </div>
    </div>
  </div>
</template>

<script>
import TaxTable from "./components/TaxTable.vue";
import { transferService } from "./service/transferService.js";
import "bootstrap/dist/css/bootstrap.min.css";

export default {
  name: "App",
  components: {
    TaxTable,
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
      transferService.getTransfers(
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
