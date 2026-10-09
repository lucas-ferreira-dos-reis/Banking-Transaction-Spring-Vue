<template>
  <div class="card shadow-sm p-4 bg-light">
    <h4 class="mb-3">Tabela de Taxas</h4>
    <table class="table table-bordered table-sm bg-white">
      <thead>
        <tr>
          <th>Faixa de dias</th>
          <th>Valor Fixo</th>
          <th>Taxa</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>0 dias</td>
          <td>R$ 3,00</td>
          <td>2,5%</td>
        </tr>
        <tr>
          <td>1 a 10 dias</td>
          <td>R$ 12,00</td>
          <td>0%</td>
        </tr>
        <tr>
          <td>11 a 20 dias</td>
          <td>R$ 0,00</td>
          <td>8,2%</td>
        </tr>
        <tr>
          <td>21 a 30 dias</td>
          <td>R$ 0,00</td>
          <td>6,9%</td>
        </tr>
        <tr>
          <td>31 a 40 dias</td>
          <td>R$ 0,00</td>
          <td>4,7%</td>
        </tr>
        <tr>
          <td>41 a 50 dias</td>
          <td>R$ 0,00</td>
          <td>1,7%</td>
        </tr>
      </tbody>
    </table>
    <small class="text-muted">
      * Fora das faixas, a transferência não é permitida.
    </small>
  </div>
</template>

<script>
export default {
  name: "TaxTable",
};
</script>
