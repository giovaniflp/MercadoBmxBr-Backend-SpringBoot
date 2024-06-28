package com.api.mercadobmxbr.advertisement.service;

import com.api.mercadobmxbr.advertisement.model.advertisementModel;
import com.api.mercadobmxbr.advertisement.repository.advertisementRepository;

import java.util.Date;
import java.util.List;
import java.io.IOException;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class advertisementService {

    //Dar uma olhada nos services necessários

    private final advertisementRepository advertisementRepository;

    String connectionString = System.getenv("CONNECTION_STRING_AZURE_STORAGE_ACCOUNT");

    @Autowired
    public advertisementService(advertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    @Transactional
    public advertisementModel findAdvertisementById(String id){
        return advertisementRepository.findById(id);
    }

    @Transactional
    public advertisementModel registerAdvertisement(advertisementModel advertisementModel){
        advertisementModel.setDataPostagem(new Date());
        return advertisementRepository.save(advertisementModel);
    }

    @Transactional
    public advertisementModel patchAdvertisement(String id, advertisementModel advertisementModel){
        advertisementModel advertisement = advertisementRepository.findById(id);

        advertisement.setDescricao(advertisementModel.getDescricao());
        advertisement.setModelo(advertisementModel.getModelo());
        advertisementModel.setDataPostagem(new Date());

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
        if (advertisementModel.getPreco() != null) {
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

    public String uploadImage(MultipartFile file) throws IOException {

        BlobContainerClient containerClient = new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName("advertisements")
                .buildClient();

        BlobClient blob = containerClient.getBlobClient(file.getOriginalFilename());

        blob.upload(file.getInputStream(), file.getSize(), true);
        return blob.getBlobUrl();
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
                .connectionString(connectionString)
                .containerName("advertisements")
                .buildClient();

        BlobClient blob = containerClient.getBlobClient(fileName);
        blob.delete();
    }

    @Transactional
    public List<advertisementModel> findAllAdvertisements() {
        return advertisementRepository.findAll();
    }

    @Transactional
    public List<advertisementModel> findAdvertisementByUser(String userId){
        return advertisementRepository.findByIdUsuario(userId);
    }

    @Transactional
    public Page<advertisementModel> getAdvertisements(String categoria, int page, int size, String sortBy, boolean asc) {
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return advertisementRepository.findByCategoria(categoria, pageable);
    }

    @Transactional
    public Page<advertisementModel> findAdvertisementByCategory(String category, int page, int size) {
        Sort sort = Sort.by("dataPostagem").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return advertisementRepository.findByCategoria(category, pageable);
    }

    @Transactional
    public Page<advertisementModel> findAdversitementByCategoryAndFilter(String categoria, String localidade, String estadoDaPeca, String dataPostagem, String marca, String valor, int page, int size){
        if("Anúncios mais recentes".equals(dataPostagem) && valor == null) {
            if(localidade != null && estadoDaPeca != null && marca != null) {
                Sort sort = Sort.by("dataPostagem").descending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndMarcaAndCategoria(localidade, estadoDaPeca, marca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca != null && marca == null) {
                Sort sort = Sort.by("dataPostagem").descending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndCategoria(localidade, estadoDaPeca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca == null && marca != null) {
                Sort sort = Sort.by("dataPostagem").descending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndMarcaAndCategoria(localidade, marca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca == null && marca == null) {
                Sort sort = Sort.by("dataPostagem").descending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndCategoria(localidade, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca != null && marca != null) {
                Sort sort = Sort.by("dataPostagem").descending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByEstadoDaPecaAndMarcaAndCategoria(estadoDaPeca, marca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca != null && marca == null) {
                Sort sort = Sort.by("dataPostagem").descending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByEstadoDaPecaAndCategoria(estadoDaPeca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca == null && marca != null) {
                Sort sort = Sort.by("dataPostagem").descending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByMarcaAndCategoria(marca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca == null && marca == null) {
                Sort sort = Sort.by("dataPostagem").descending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByCategoria(categoria, pageable);
            }
        }
        if("Anúncios mais recentes".equals(dataPostagem) && "Menor Valor".equals(valor)) {
            if(localidade != null && estadoDaPeca != null && marca != null) {
                Sort sort = Sort.by(Sort.Order.desc("dataPostagem"), Sort.Order.asc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndMarcaAndCategoria(localidade, estadoDaPeca, marca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca != null && marca == null) {
                Sort sort = Sort.by(Sort.Order.desc("dataPostagem"), Sort.Order.asc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndCategoria(localidade, estadoDaPeca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca == null && marca != null) {
                Sort sort = Sort.by(Sort.Order.desc("dataPostagem"), Sort.Order.asc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndMarcaAndCategoria(localidade, marca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca == null && marca == null) {
                Sort sort = Sort.by(Sort.Order.desc("dataPostagem"), Sort.Order.asc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndCategoria(localidade, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca != null && marca != null) {
                Sort sort = Sort.by(Sort.Order.desc("dataPostagem"), Sort.Order.asc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByEstadoDaPecaAndMarcaAndCategoria(estadoDaPeca, marca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca != null && marca == null) {
                Sort sort = Sort.by(Sort.Order.desc("dataPostagem"), Sort.Order.asc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByEstadoDaPecaAndCategoria(estadoDaPeca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca == null && marca != null) {
                Sort sort = Sort.by(Sort.Order.desc("dataPostagem"), Sort.Order.asc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByMarcaAndCategoria(marca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca == null && marca == null) {
                Sort sort = Sort.by(Sort.Order.desc("dataPostagem"), Sort.Order.asc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByCategoria(categoria, pageable);
            }
        }
        if("Anúncios mais recentes".equals(dataPostagem) && "Maior Valor".equals(valor)) {
            if(localidade != null && estadoDaPeca != null && marca != null) {
                Sort sort = Sort.by(Sort.Order.desc("dataPostagem"), Sort.Order.desc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndMarcaAndCategoria(localidade, estadoDaPeca, marca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca != null && marca == null) {
                Sort sort = Sort.by(Sort.Order.desc("dataPostagem"), Sort.Order.desc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndCategoria(localidade, estadoDaPeca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca == null && marca != null) {
                Sort sort = Sort.by(Sort.Order.desc("dataPostagem"), Sort.Order.desc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndMarcaAndCategoria(localidade, marca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca == null && marca == null) {
                Sort sort = Sort.by(Sort.Order.desc("dataPostagem"), Sort.Order.desc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndCategoria(localidade, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca != null && marca != null) {
                Sort sort = Sort.by(Sort.Order.desc("dataPostagem"), Sort.Order.desc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByEstadoDaPecaAndMarcaAndCategoria(estadoDaPeca, marca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca != null && marca == null) {
                Sort sort = Sort.by(Sort.Order.desc("dataPostagem"), Sort.Order.desc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByEstadoDaPecaAndCategoria(estadoDaPeca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca == null && marca != null) {
                Sort sort = Sort.by(Sort.Order.desc("dataPostagem"), Sort.Order.desc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByMarcaAndCategoria(marca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca == null && marca == null) {
                Sort sort = Sort.by(Sort.Order.desc("dataPostagem"), Sort.Order.desc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByCategoria(categoria, pageable);
            }
        }
        if("Anúncios mais antigos".equals(dataPostagem) && valor == null) {
            if(localidade != null && estadoDaPeca != null && marca != null) {
                Sort sort = Sort.by("dataPostagem").ascending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndMarcaAndCategoria(localidade, estadoDaPeca, marca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca != null && marca == null) {
                Sort sort = Sort.by("dataPostagem").ascending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndCategoria(localidade, estadoDaPeca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca == null && marca != null) {
                Sort sort = Sort.by("dataPostagem").ascending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndMarcaAndCategoria(localidade, marca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca == null && marca == null) {
                Sort sort = Sort.by("dataPostagem").ascending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndCategoria(localidade, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca != null && marca != null) {
                Sort sort = Sort.by("dataPostagem").ascending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByEstadoDaPecaAndMarcaAndCategoria(estadoDaPeca, marca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca != null && marca == null) {
                Sort sort = Sort.by("dataPostagem").ascending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByEstadoDaPecaAndCategoria(estadoDaPeca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca == null && marca != null) {
                Sort sort = Sort.by("dataPostagem").ascending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByMarcaAndCategoria(marca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca == null && marca == null) {
                Sort sort = Sort.by("dataPostagem").ascending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByCategoria(categoria, pageable);
            }
        }
        if("Anúncios mais antigos".equals(dataPostagem) && "Menor Valor".equals(valor)) {
            if(localidade != null && estadoDaPeca != null && marca != null) {
                Sort sort = Sort.by(Sort.Order.asc("dataPostagem"), Sort.Order.asc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndMarcaAndCategoria(localidade, estadoDaPeca, marca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca != null && marca == null) {
                Sort sort = Sort.by(Sort.Order.asc("dataPostagem"), Sort.Order.asc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndCategoria(localidade, estadoDaPeca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca == null && marca != null) {
                Sort sort = Sort.by(Sort.Order.asc("dataPostagem"), Sort.Order.asc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndMarcaAndCategoria(localidade, marca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca == null && marca == null) {
                Sort sort = Sort.by(Sort.Order.asc("dataPostagem"), Sort.Order.asc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndCategoria(localidade, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca != null && marca != null) {
                Sort sort = Sort.by(Sort.Order.asc("dataPostagem"), Sort.Order.asc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByEstadoDaPecaAndMarcaAndCategoria(estadoDaPeca, marca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca != null && marca == null) {
                Sort sort = Sort.by(Sort.Order.asc("dataPostagem"), Sort.Order.asc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByEstadoDaPecaAndCategoria(estadoDaPeca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca == null && marca != null) {
                Sort sort = Sort.by(Sort.Order.asc("dataPostagem"), Sort.Order.asc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByMarcaAndCategoria(marca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca == null && marca == null) {
                Sort sort = Sort.by(Sort.Order.asc("dataPostagem"), Sort.Order.asc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByCategoria(categoria, pageable);
            }
        }
        if("Anúncios mais antigos".equals(dataPostagem) && "Maior Valor".equals(valor)) {
            if(localidade != null && estadoDaPeca != null && marca != null) {
                Sort sort = Sort.by(Sort.Order.asc("dataPostagem"), Sort.Order.desc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndMarcaAndCategoria(localidade, estadoDaPeca, marca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca != null && marca == null) {
                Sort sort = Sort.by(Sort.Order.asc("dataPostagem"), Sort.Order.desc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndCategoria(localidade, estadoDaPeca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca == null && marca != null) {
                Sort sort = Sort.by(Sort.Order.asc("dataPostagem"), Sort.Order.desc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndMarcaAndCategoria(localidade, marca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca == null && marca == null) {
                Sort sort = Sort.by(Sort.Order.asc("dataPostagem"), Sort.Order.desc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndCategoria(localidade, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca != null && marca != null) {
                Sort sort = Sort.by(Sort.Order.asc("dataPostagem"), Sort.Order.desc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByEstadoDaPecaAndMarcaAndCategoria(estadoDaPeca, marca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca != null && marca == null) {
                Sort sort = Sort.by(Sort.Order.asc("dataPostagem"), Sort.Order.desc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByEstadoDaPecaAndCategoria(estadoDaPeca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca == null && marca != null) {
                Sort sort = Sort.by(Sort.Order.asc("dataPostagem"), Sort.Order.desc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByMarcaAndCategoria(marca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca == null && marca == null) {
                Sort sort = Sort.by(Sort.Order.asc("dataPostagem"), Sort.Order.desc("preco"));
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByCategoria(categoria, pageable);
            }
        }
        if(dataPostagem == null && "Menor Valor".equals(valor)) {
            if(localidade != null && estadoDaPeca != null && marca != null) {
                Sort sort = Sort.by("preco").ascending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndMarcaAndCategoria(localidade, estadoDaPeca, marca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca != null && marca == null) {
                Sort sort = Sort.by("preco").ascending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndCategoria(localidade, estadoDaPeca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca == null && marca != null) {
                Sort sort = Sort.by("preco").ascending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndMarcaAndCategoria(localidade, marca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca == null && marca == null) {
                Sort sort = Sort.by("preco").ascending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndCategoria(localidade, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca != null && marca != null) {
                Sort sort = Sort.by("preco").ascending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByEstadoDaPecaAndMarcaAndCategoria(estadoDaPeca, marca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca != null && marca == null) {
                Sort sort = Sort.by("preco").ascending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByEstadoDaPecaAndCategoria(estadoDaPeca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca == null && marca != null) {
                Sort sort = Sort.by("preco").ascending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByMarcaAndCategoria(marca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca == null && marca == null) {
                Sort sort = Sort.by("preco").ascending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByCategoria(categoria, pageable);
            }
        }
        if(dataPostagem == null && "Maior Valor".equals(valor)) {
            if(localidade != null && estadoDaPeca != null && marca != null) {
                Sort sort = Sort.by("preco").descending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndMarcaAndCategoria(localidade, estadoDaPeca, marca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca != null && marca == null) {
                Sort sort = Sort.by("preco").descending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndCategoria(localidade, estadoDaPeca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca == null && marca != null) {
                Sort sort = Sort.by("preco").descending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndMarcaAndCategoria(localidade, marca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca == null && marca == null) {
                Sort sort = Sort.by("preco").descending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByLocalidadeAndCategoria(localidade, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca != null && marca != null) {
                Sort sort = Sort.by("preco").descending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByEstadoDaPecaAndMarcaAndCategoria(estadoDaPeca, marca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca != null && marca == null) {
                Sort sort = Sort.by("preco").descending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByEstadoDaPecaAndCategoria(estadoDaPeca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca == null && marca != null) {
                Sort sort = Sort.by("preco").descending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByMarcaAndCategoria(marca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca == null && marca == null) {
                Sort sort = Sort.by("preco").descending();
                Pageable pageable = PageRequest.of(page, size, sort);
                return advertisementRepository.findByCategoria(categoria, pageable);
            }
        }
        if(dataPostagem == null && valor == null) {
            if(localidade != null && estadoDaPeca != null && marca != null) {
                Pageable pageable = PageRequest.of(page, size);
                return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndMarcaAndCategoria(localidade, estadoDaPeca, marca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca != null && marca == null) {
                Pageable pageable = PageRequest.of(page, size);
                return advertisementRepository.findByLocalidadeAndEstadoDaPecaAndCategoria(localidade, estadoDaPeca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca == null && marca != null) {
                Pageable pageable = PageRequest.of(page, size);
                return advertisementRepository.findByLocalidadeAndMarcaAndCategoria(localidade, marca, categoria, pageable);
            }
            if(localidade != null && estadoDaPeca == null && marca == null) {
                Pageable pageable = PageRequest.of(page, size);
                return advertisementRepository.findByLocalidadeAndCategoria(localidade, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca != null && marca != null) {
                Pageable pageable = PageRequest.of(page, size);
                return advertisementRepository.findByEstadoDaPecaAndMarcaAndCategoria(estadoDaPeca, marca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca != null && marca == null) {
                Pageable pageable = PageRequest.of(page, size);
                return advertisementRepository.findByEstadoDaPecaAndCategoria(estadoDaPeca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca == null && marca != null) {
                Pageable pageable = PageRequest.of(page, size);
                return advertisementRepository.findByMarcaAndCategoria(marca, categoria, pageable);
            }
            if(localidade == null && estadoDaPeca == null && marca == null) {
                Pageable pageable = PageRequest.of(page, size);
                return advertisementRepository.findByCategoria(categoria, pageable);
            }
        }
        return null;
    }

    @Transactional
    public Page<advertisementModel> findAdvertisementByDataPostagemAndEstadoDaPeca(String estadoDaPeca, int page, int size) {
        Sort sort = Sort.by("dataPostagem").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return advertisementRepository.findByEstadoDaPeca(estadoDaPeca, pageable);
    }

    @Transactional
    public Page<advertisementModel> findAdvertisementByLocalidade(String localidade, int page, int size) {
        Sort sort = Sort.by("dataPostagem").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return advertisementRepository.findByLocalidade(localidade, pageable);
    }

}
