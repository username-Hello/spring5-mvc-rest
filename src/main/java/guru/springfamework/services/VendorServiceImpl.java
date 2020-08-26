package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {
    
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;
    
    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }
    
    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendorDTO.getId()));
                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDTO)
                .map(vendorDTO -> {
                    vendorDTO.setVendorUrl(getVendorUrl(vendorDTO.getId()));
                    return vendorDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }
    
    public VendorDTO createNewVendor(VendorDTO vendorDto) {
        return saveAndReturnDTO(vendorMapper.vendorDTOToVendor(vendorDto));
    }
    
    private VendorDTO saveAndReturnDTO(Vendor vendor) {
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
        vendorDTO.setVendorUrl(getVendorUrl(vendorDTO.getId()));
        return vendorDTO;
    }
    
    private String getVendorUrl(Long id) {
        return (VendorController.BASE_URL + "/" + id);
    }
}
