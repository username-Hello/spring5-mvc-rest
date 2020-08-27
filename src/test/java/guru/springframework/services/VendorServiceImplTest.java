package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.controllers.v1.VendorController;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class VendorServiceImplTest {
    
    public static final String NAME_1 = "My Vendor";
    public static final long ID_1 = 1L;
    public static final String NAME_2 = "My Vendor";
    public static final long ID_2 = 1L;
    
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
    
    @Test
    public void saveVendorByDTO() {
        //given
        
        Vendor vendor = new Vendor();
        vendor.setId(1l);
        vendor.setName("Jim");
        
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
        
        //when
        VendorDTO savedDto = vendorService.saveVendorByDTO(1l, vendorMapper.vendorToVendorDTO(vendor));
        
        //then
        assertEquals(vendor.getName(), savedDto.getName());
        assertEquals(VendorController.BASE_URL + "/1", savedDto.getVendorUrl());
    }
    
    @Test
    public void patchVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_1);
        
        Vendor vendor = getVendor1();
        
        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);
        
        //when
        
        VendorDTO savedVendorDTO = vendorService.patchVendor(ID_1, vendorDTO);
        
        //then
        // 'should' defaults to times = 1
        then(vendorRepository).should().save(any(Vendor.class));
        then(vendorRepository).should(times(1)).findById(anyLong());
        assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
    }
    
    @Test
    public void deleteVendor() {
        //when
        vendorService.deleteVendorById(1L);
        
        //then
        then(vendorRepository).should().deleteById(anyLong());
    }
    
    private Vendor getVendor1() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME_1);
        vendor.setId(ID_1);
        return vendor;
    }
}