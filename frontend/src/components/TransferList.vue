<template>
  <div class="card shadow-sm p-4">
    <h4 class="mb-3">Lista de Agendamentos</h4>
    <div class="table-responsive">
      <table class="table table-striped table-hover">
        <thead>
          <tr>
            <th>ID</th>
            <th>Origem</th>
            <th>Destino</th>
            <th>Valor (R$)</th>
            <th>Taxa (R$)</th>
            <th>Data Agendamento</th>
            <th>Data Transferência</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="t in transfers" :key="t.id">
            <td>{{ t.id }}</td>
            <td>{{ t.sourceAccount }}</td>
            <td>{{ t.destinationAccount }}</td>
            <td>R$ {{ Number(t.transferAmount).toFixed(2) }}</td>
            <td>R$ {{ Number(t.feeAmount).toFixed(2) }}</td>
            <td>{{ t.scheduleDate }}</td>
            <td>{{ t.transferDate }}</td>
          </tr>
          <tr v-if="transfers.length === 0">
            <td colspan="7" class="text-center text-muted">
              Nenhum agendamento cadastrado.
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name: "TransferList",
  props: {
    transfers: {
      type: Array,
      required: true,
    },
  },
};
</script>
