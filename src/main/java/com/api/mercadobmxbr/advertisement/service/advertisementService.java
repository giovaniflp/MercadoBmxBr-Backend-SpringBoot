package com.api.mercadobmxbr.advertisement.service;
import com.api.mercadobmxbr.advertisement.model.advertisementModel;
import com.api.mercadobmxbr.advertisement.repository.advertisementRepository;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class advertisementService {

    @Autowired
    private advertisementRepository advertisementRepository;

    @Transactional
    public List<advertisementModel> findAllAdvertisements() {
        return advertisementRepository.findAll();
    }

    @Transactional
    public advertisementModel findAdvertisementById(String id){
        return advertisementRepository.findById(id);
    }

    @Transactional
    public List<advertisementModel> findAdvertisementByCategory(String category){
        return advertisementRepository.findByCategoria(category);
    }

    @Transactional
    public List<advertisementModel> findByLocalidadeAndEstadoDaPecaAndCategoria(String localidade,  String estadoDaPeca, String category){
        return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndCategoria(localidade, estadoDaPeca, category);
    }

    @Transactional
    public List<advertisementModel> findByLocalidadeAndCategoria(String localidade, String category){
        return advertisementRepository.findByLocalidadeAndCategoria(localidade, category);
    }

    @Transactional
    public List<advertisementModel> findByEstadoDaPecaAndCategoria(String estadoDaPeca, String category){
        return advertisementRepository.findByEstadoDaPecaAndCategoria(estadoDaPeca, category);
    }

    @Transactional
    public List<advertisementModel> findAdvertisementByUser(String userId){
        return advertisementRepository.findByIdUsuario(userId);
    }

    @Transactional
    public advertisementModel registerAdvertisement(advertisementModel advertisementModel){
        java.time.LocalDate currentDate = java.time.LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        advertisementModel.setDataPostagem(formattedDate);
        return advertisementRepository.save(advertisementModel);
    }

    public String uploadImage(MultipartFile file) throws IOException {

        BlobContainerClient containerClient = new BlobContainerClientBuilder()
                .connectionString("DefaultEndpointsProtocol=https;AccountName=mercadobmxbr;AccountKey=wv3hSdDl89gy9Fj2KzMCTuUfpjiywNyNyLg+HD2AJz9q8B7H1FUTYitjM94BjVfsxhMzr4r88iXt+AStwfdpVA==;EndpointSuffix=core.windows.net")
                .containerName("advertisements")
                .buildClient();

        BlobClient blob = containerClient.getBlobClient(file.getOriginalFilename());

        blob.upload(file.getInputStream(), file.getSize(), true);
        return blob.getBlobUrl();
    }

    @Transactional
    public void deleteAdvertisement(String id) {
        advertisementModel advertisement = advertisementRepository.findById(id);
        if (advertisement != null) {
            // Extract image file name from the URL
            String imageUrl = advertisement.getImagem();
            String fileName = extractFileNameFromUrl(imageUrl);

            // Delete advertisement from database
            advertisementRepository.deleteById(id);

            // Delete image from Azure storage
            if (fileName != null) {
                deleteImage(fileName);
            }
        }
    }

    private void deleteImage(String fileName) {
        BlobContainerClient containerClient = new BlobContainerClientBuilder()
                .connectionString("DefaultEndpointsProtocol=https;AccountName=mercadobmxbr;AccountKey=wv3hSdDl89gy9Fj2KzMCTuUfpjiywNyNyLg+HD2AJz9q8B7H1FUTYitjM94BjVfsxhMzr4r88iXt+AStwfdpVA==;EndpointSuffix=core.windows.net")
                .containerName("advertisements")
                .buildClient();

        BlobClient blob = containerClient.getBlobClient(fileName);
        blob.delete();
    }

    private String extractFileNameFromUrl(String url) {
        // Extract file name from the URL
        int index = url.lastIndexOf("/");
        if (index != -1) {
            return url.substring(index + 1);
        }
        return null;
    }

    @Transactional
    public advertisementModel patchAdvertisement(String id, advertisementModel advertisementModel){
        advertisementModel advertisement = advertisementRepository.findById(id);

        advertisement.setDescricao(advertisementModel.getDescricao());
        advertisement.setModelo(advertisementModel.getModelo());
        java.time.LocalDate currentDate = java.time.LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        advertisement.setDataPostagem(formattedDate);

        if(advertisementModel.getEstadoDaPeca() != null && !advertisementModel.getEstadoDaPeca().isEmpty()){
            advertisement.setEstadoDaPeca(advertisementModel.getEstadoDaPeca());
        }
        if(advertisementModel.getGrauDeDesgaste() != null && !advertisementModel.getGrauDeDesgaste().isEmpty()){
            advertisement.setGrauDeDesgaste(advertisementModel.getGrauDeDesgaste());
        }
        if(advertisementModel.getImagem() != null && !advertisementModel.getImagem().isEmpty()){
            String imageUrl = advertisement.getImagem();
            String fileName = extractFileNameFromUrl(imageUrl);
            if (fileName != null) {
                deleteImage(fileName);
            }
            advertisement.setImagem(advertisementModel.getImagem());
        }
        if(advertisementModel.getLocalidade() != null && !advertisementModel.getLocalidade().isEmpty()){
            advertisement.setLocalidade(advertisementModel.getLocalidade());
        }
        if(advertisementModel.getPreco() != null && !advertisementModel.getPreco().isEmpty()){
            advertisement.setPreco(advertisementModel.getPreco());
        }
        if(advertisementModel.getWhatsapp() != null && !advertisementModel.getWhatsapp().isEmpty()){
            advertisement.setWhatsapp(advertisementModel.getWhatsapp());
        }
        if(advertisementModel.getCategoria() != null && !advertisementModel.getCategoria().isEmpty()){
            advertisement.setCategoria(advertisementModel.getCategoria());
        }
        if(advertisementModel.getCor() != null && !advertisementModel.getCor().isEmpty()){
            advertisement.setCor(advertisementModel.getCor());
        }
        if(advertisementModel.getMarca() != null && !advertisementModel.getMarca().isEmpty()){
            advertisement.setMarca(advertisementModel.getMarca());
        }
        if(advertisementModel.getPeso() != null && !advertisementModel.getPeso().isEmpty()){
            advertisement.setPeso(advertisementModel.getPeso());
        }

        //Não obrigatórios

        advertisement.setAbracadeiraDiametro(advertisementModel.getAbracadeiraDiametro());
        advertisement.setAroTipoFolha(advertisementModel.getAroTipoFolha());
        advertisement.setAroFuros(advertisementModel.getAroFuros());
        advertisement.setAroGrossura(advertisementModel.getAroGrossura());
        advertisement.setBancoTipo(advertisementModel.getBancoTipo());
        advertisement.setBancoCanoteTamanho(advertisementModel.getBancoCanoteTamanho());
        advertisement.setBikeCompletaModalidade(advertisementModel.getBikeCompletaModalidade());
        advertisement.setCamaraAroTamanho(advertisementModel.getCamaraAroTamanho());
        advertisement.setCamaraTipoValvula(advertisementModel.getCamaraTipoValvula());
        advertisement.setCanoteTipo(advertisementModel.getCanoteTipo());
        advertisement.setCanoteTamanho(advertisementModel.getCanoteTamanho());
        advertisement.setCoroaDentes(advertisementModel.getCoroaDentes());
        advertisement.setCoroaProtetor(advertisementModel.getCoroaProtetor());
        advertisement.setCoroaAdaptador(advertisementModel.getCoroaAdaptador());
        advertisement.setCorrenteTipoElo(advertisementModel.getCorrenteTipoElo());
        advertisement.setCuboDianteiroFuros(advertisementModel.getCuboDianteiroFuros());
        advertisement.setCuboDianteiroTipoEixo(advertisementModel.getCuboDianteiroTipoEixo());
        advertisement.setCuboDianteiroMaterialEixo(advertisementModel.getCuboDianteiroMaterialEixo());
        advertisement.setCuboDianteiroMaterialParafusos(advertisementModel.getCuboDianteiroMaterialParafusos());
        advertisement.setCuboDianteiroProtetor(advertisementModel.getCuboDianteiroProtetor());
        advertisement.setTipoCubo(advertisementModel.getTipoCubo());
        advertisement.setCuboTraseiroTracao(advertisementModel.getCuboTraseiroTracao());
        advertisement.setCuboTraseiroCog(advertisementModel.getCuboTraseiroCog());
        advertisement.setCuboTraseiroTravas(advertisementModel.getCuboTraseiroTravas());
        advertisement.setCuboTraseiroFuros(advertisementModel.getCuboTraseiroFuros());
        advertisement.setCuboTraseiroTipoEixo(advertisementModel.getCuboTraseiroTipoEixo());
        advertisement.setCuboTraseiroMaterialEixo(advertisementModel.getCuboTraseiroMaterialEixo());
        advertisement.setCuboTraseiroMaterialParafusos(advertisementModel.getCuboTraseiroMaterialParafusos());
        advertisement.setCuboTraseiroProtetor(advertisementModel.getCuboTraseiroProtetor());
        advertisement.setEixoCentralEstrias(advertisementModel.getEixoCentralEstrias());
        advertisement.setEixoCentralTamanho(advertisementModel.getEixoCentralTamanho());
        advertisement.setFreioPeca(advertisementModel.getFreioPeca());
        advertisement.setGarfoOffset(advertisementModel.getGarfoOffset());
        advertisement.setGarfoTampa(advertisementModel.getGarfoTampa());
        advertisement.setGuidaoTamanho(advertisementModel.getGuidaoTamanho());
        advertisement.setGuidaoLargura(advertisementModel.getGuidaoLargura());
        advertisement.setGuidaoAngulo(advertisementModel.getGuidaoAngulo());
        advertisement.setGuidaoTipo(advertisementModel.getGuidaoTipo());
        advertisement.setManoplaTamanho(advertisementModel.getManoplaTamanho());
        advertisement.setManoplaBarEnds(advertisementModel.getManoplaBarEnds());
        advertisement.setMesaTamanho(advertisementModel.getMesaTamanho());
        advertisement.setMesaAltura(advertisementModel.getMesaAltura());
        advertisement.setMesaTipo(advertisementModel.getMesaTipo());
        advertisement.setMesaFabricacao(advertisementModel.getMesaFabricacao());
        advertisement.setMovimentoCentralTipo(advertisementModel.getMovimentoCentralTipo());
        advertisement.setMovimentoCentralRolamento(advertisementModel.getMovimentoCentralRolamento());
        advertisement.setMovimentoCentralAcompanha(advertisementModel.getMovimentoCentralAcompanha());
        advertisement.setMovimentoDirecaoTipo(advertisementModel.getMovimentoDirecaoTipo());
        advertisement.setMovimentoDirecaoTampa(advertisementModel.getMovimentoDirecaoTampa());
        advertisement.setMovimentoDirecaoAcompanha(advertisementModel.getMovimentoDirecaoAcompanha());
        advertisement.setPedalRosca(advertisementModel.getPedalRosca());
        advertisement.setPedalConstrucao(advertisementModel.getPedalConstrucao());
        advertisement.setPedaleiraQuantidade(advertisementModel.getPedaleiraQuantidade());
        advertisement.setPedaleiraEncaixe(advertisementModel.getPedaleiraEncaixe());
        advertisement.setPedaleiraTamanho(advertisementModel.getPedaleiraTamanho());
        advertisement.setPedivelaTracao(advertisementModel.getPedivelaTracao());
        advertisement.setPedivelaTamanho(advertisementModel.getPedivelaTamanho());
        advertisement.setPedivelaRolamento(advertisementModel.getPedivelaRolamento());
        advertisement.setPedivelaEstrias(advertisementModel.getPedivelaEstrias());
        advertisement.setPedivelaAcompanha(advertisementModel.getPedivelaAcompanha());
        advertisement.setPedivelaConstrucao(advertisementModel.getPedivelaConstrucao());
        advertisement.setPneuAro(advertisementModel.getPneuAro());
        advertisement.setPneuBandaLateral(advertisementModel.getPneuBandaLateral());
        advertisement.setPneuIndicacao(advertisementModel.getPneuIndicacao());
        advertisement.setPneuTamanho(advertisementModel.getPneuTamanho());
        advertisement.setQuadroAbracadeira(advertisementModel.getQuadroAbracadeira());
        advertisement.setQuadroCentral(advertisementModel.getQuadroCentral());
        advertisement.setQuadroDirecao(advertisementModel.getQuadroDirecao());
        advertisement.setQuadroEsticador(advertisementModel.getQuadroEsticador());
        advertisement.setQuadroMedida(advertisementModel.getQuadroMedida());
        advertisement.setQuadroModalidade(advertisementModel.getQuadroModalidade());
        advertisement.setQuadroPinos(advertisementModel.getQuadroPinos());
        advertisement.setQuadroTamanhoAro(advertisementModel.getQuadroTamanhoAro());
        advertisement.setQuadroTolerancia(advertisementModel.getQuadroTolerancia());
        advertisement.setProtetorLado(advertisementModel.getProtetorLado());
        advertisement.setRaioTipo(advertisementModel.getRaioTipo());
        advertisement.setRaioTamanho(advertisementModel.getRaioTamanho());


        return advertisementRepository.save(advertisement);
    }

}
