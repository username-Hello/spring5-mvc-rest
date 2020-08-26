package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class VendorServiceImplTest {
    
    @Mock
    VendorRepository vendorRepository;
    VendorMapper vendorMapper = VendorMapper.INSTANCE;
    VendorService vendorService;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(vendorRepository, vendorMapper);
    }
    
    @Test
    public void getAllVendors() {
        when(vendorRepository.findAll()).thenReturn(Arrays.asList(new Vendor(), new Vendor()));
        
        // when
        List<VendorDTO> vendorDTOs = vendorService.getAllVendors();
        
        // then
        assertThat(vendorDTOs.size(), equalTo(2));
    }
    
    @Test
    public void getVendorById() {
        Vendor vendor = new Vendor();
        vendor.setId(1l);
        vendor.setName("Michale");
        
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        
        VendorDTO findVendor = vendorService.getVendorById(1l);
        
        assertThat(findVendor.getId(), equalTo(vendor.getId()));
        assertThat(findVendor.getName(), equalTo(vendor.getName()));
    }
}