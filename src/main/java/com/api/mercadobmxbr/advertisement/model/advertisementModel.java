package com.api.mercadobmxbr.advertisement.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "advertisements")
public class advertisementModel {
    @NotNull
    @Id private String id;
    //Not Null
    @NotNull
    private String idUsuario;
    @NotNull
    private String nomeUsuario;
    @NotNull
    private String categoria;
    @NotNull
    private String marca;
    @NotNull
    private String modelo;
    @NotNull
    private String cor;
    @NotNull
    private String preco;
    @NotNull
    private String localidade;
    @NotNull
    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date dataPostagem;
    //Estará hospedado na nuvem, então o que será salvo no banco é o link da imagem
    @NotNull
    private String imagem;
    @NotNull
    private String estadoDaPeca;
    @NotNull
    private String grauDeDesgaste;
    @NotNull
    private String material;
    @NotNull
    private String peso;
    @NotNull
    private String descricao;
    @NotNull
    private String whatsapp;

    //Can be null
    @Nullable
    private String abracadeiraDiametro;
    @Nullable
    private String aroTipoFolha;
    @Nullable
    private String aroFuros;
    @Nullable
    private String aroGrossura;
    @Nullable
    private String bancoTipo;
    @Nullable
    private String bancoCanoteTamanho;
    @Nullable
    private String bikeCompletaModalidade;
    @Nullable
    private String camaraAroTamanho;
    @Nullable
    private String camaraTipoValvula;
    @Nullable
    private String canoteTipo;
    @Nullable
    private String canoteTamanho;
    @Nullable
    private String coroaDentes;
    @Nullable
    private String coroaProtetor;
    @Nullable
    private String coroaAdaptador;
    @Nullable
    private String correnteTipoElo;
    @Nullable
    private String cuboDianteiroFuros;
    @Nullable
    private String cuboDianteiroTipoEixo;
    @Nullable
    private String cuboDianteiroMaterialEixo;
    @Nullable
    private String cuboDianteiroMaterialParafusos;
    @Nullable
    private String cuboDianteiroProtetor;
    @Nullable
    private String tipoCubo;
    @Nullable
    private String cuboTraseiroTracao;
    @Nullable
    private String cuboTraseiroCog;
    @Nullable
    private String cuboTraseiroTravas;
    @Nullable
    private String cuboTraseiroFuros;
    @Nullable
    private String cuboTraseiroTipoEixo;
    @Nullable
    private String cuboTraseiroMaterialEixo;
    @Nullable
    private String cuboTraseiroMaterialParafusos;
    @Nullable
    private String cuboTraseiroProtetor;
    @Nullable
    private String eixoCentralEstrias;
    @Nullable
    private String eixoCentralTamanho;
    @Nullable
    private String freioPeca;
    @Nullable
    private String garfoOffset;
    @Nullable
    private String garfoTampa;
    @Nullable
    private String guidaoTamanho;
    @Nullable
    private String guidaoLargura;
    @Nullable
    private String guidaoAngulo;
    @Nullable
    private String guidaoTipo;
    @Nullable
    private String manoplaTamanho;
    @Nullable
    private String manoplaBarEnds;
    @Nullable
    private String mesaTamanho;
    @Nullable
    private String mesaAltura;
    @Nullable
    private String mesaTipo;
    @Nullable
    private String mesaFabricacao;
    @Nullable
    private String movimentoCentralTipo;
    @Nullable
    private String movimentoCentralRolamento;
    @Nullable
    private String movimentoCentralAcompanha;
    @Nullable
    private String movimentoDirecaoTipo;
    @Nullable
    private String movimentoDirecaoTampa;
    @Nullable
    private String movimentoDirecaoAcompanha;
    @Nullable
    private String pedalRosca;
    @Nullable
    private String pedalConstrucao;
    @Nullable
    private String pedaleiraQuantidade;
    @Nullable
    private String pedaleiraEncaixe;
    @Nullable
    private String pedaleiraTamanho;
    @Nullable
    private String pedivelaTracao;
    @Nullable
    private String pedivelaTamanho;
    @Nullable
    private String pedivelaRolamento;
    @Nullable
    private String pedivelaEstrias;
    @Nullable
    private String pedivelaAcompanha;
    @Nullable
    private String pedivelaConstrucao;
    @Nullable
    private String pneuAro;
    @Nullable
    private String pneuBandaLateral;
    @Nullable
    private String pneuIndicacao;
    @Nullable
    private String pneuTamanho;
    @Nullable
    private String quadroAbracadeira;
    @Nullable
    private String quadroCentral;
    @Nullable
    private String quadroDirecao;
    @Nullable
    private String quadroEsticador;
    @Nullable
    private String quadroMedida;
    @Nullable
    private String quadroModalidade;
    @Nullable
    private String quadroPinos;
    @Nullable
    private String quadroTamanhoAro;
    @Nullable
    private String quadroTolerancia;
    @Nullable
    private String protetorLado;
    @Nullable
    private String raioTipo;
    @Nullable
    private String raioTamanho;
}
