package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class VendorMapperTest {
    
    public static Long ID = 1L;
    public static String NAME = "John";
    VendorMapper vendorMapper = VendorMapper.INSTANCE;
    
    @Test
    public void vendorToVendorDTO() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);
        
        // when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
        
        assertThat(vendorDTO.getId(), equalTo(vendorDTO.getId()));
        assertThat(vendorDTO.getName(), equalTo(vendorDTO.getName()));
    }
    
    @Test
    public void vendorDTOToVendor() {
        
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(ID);
        vendorDTO.setName(NAME);
        
        // when
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        
        assertThat(vendorDTO.getId(), equalTo(vendor.getId()));
        assertThat(vendorDTO.getName(), equalTo(vendor.getName()));
    }
}