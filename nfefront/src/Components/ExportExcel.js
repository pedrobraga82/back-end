import React from "react";
import ReactExport from "react-data-export";
import 'materialize-css';

const ExcelFile = ReactExport.ExcelFile;
const ExcelSheet = ReactExport.ExcelFile.ExcelSheet;
const ExcelColumn = ReactExport.ExcelFile.ExcelColumn;



          {/*   Chave	SPED	Emissão	Número	Série	Tipo	Valor	Status	Emitente CNPJ	
            Emitente	Emitente IE	Emitente UF	Destinatário CNPJ/CPF	Destinatário
                Destinatário IE	Destinatário UF */}
 
export default function ExportExcel({dataSet}) {
    return (
        <div>
            
            <ExcelFile element={
                          <div class="card-panel z-depth-2">

                        <a class="waves-effect waves-red waves-light btn-small">
                            <i class="material-icons left">cloud</i>Exportar para Excel</a>
                        </div>
                }>
                <ExcelSheet data={dataSet} name="NFEs">
                    <ExcelColumn label="Chave"  value="chaveNfe"/>
                    <ExcelColumn label="Data Emissão"  value="dataemissao"/>
                    <ExcelColumn label="Número Protocolo"  value="numeroprotocolo"/>
                    <ExcelColumn label="Tipo"  value="tipo" /> 
                    <ExcelColumn label="Valor" value="valor" />
                    <ExcelColumn label="Status"  value="status" />
                    <ExcelColumn label="Emitente CNPJ"  value="emitentecnpj" />
                    <ExcelColumn label="Emitente"  value="emitente" />
                    <ExcelColumn label="Emitente IE"  value="emitenteIE" />
                    <ExcelColumn label="Emitente UF"  value="emitenteUF" />
                    <ExcelColumn label="Destinatário"  value="destinatario" />
                    <ExcelColumn label="Destinatário CNPJ"  value="destinatariocnpj" />
                    <ExcelColumn label="Destinatário IE"  value="destinatarioIE" />
                    <ExcelColumn label="Destinatário UF"  value="destinatarioUF" />
                </ExcelSheet>
              
              
            </ExcelFile>






        </div>
    )
}
