import MaterialTable from 'material-table';



export default function NFETable({dados}) {
    return (
      <MaterialTable
        title="Visualização de Nfes"
        columns={[
          {
            title: 'Nfe', 
            field: 'chnfe',
//            customFilterAndSearch: (term, rowData) => term == rowData.nfe.length
            
          },
          { title: 'IE', field: 'ie', filtering: false },
          { title: 'CNPJ', field: 'cnpjremetente',filtering: false },
          { title: 'Nome', field: 'nome',filtering: false },
          { title: 'Valor', field: 'valor',filtering: false },
      
          
        ]}
        data={dados}
        options={{
          filtering: true
        }}
      />
    )
  }