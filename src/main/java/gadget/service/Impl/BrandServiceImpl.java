package gadget.service.Impl;

import gadget.dto.brand.BrandRequest;
import gadget.dto.brand.BrandRequest2;
import gadget.dto.brand.BrandResponse;
import gadget.dto.user.SimpleResponse;
import gadget.entity.Brand;
import gadget.exceptions.NotFoundException;
import gadget.repo.BrandRepo;
import gadget.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepo brandRepo;

    @Override
    public BrandResponse saveBrand(BrandRequest brandRequest) {
        Brand brand=Brand.builder()
                .brandName(brandRequest.brandName())
                .image(brandRequest.image())
                .build();
        brandRepo.save(brand);

        return BrandResponse.builder()
                .brandName(brand.getBrandName())
                .build();
    }

    @Override
    public SimpleResponse deleteBrand(String branName) {
        Brand brand = brandRepo.getBrandByBrandName(branName).orElseThrow(() -> new NotFoundException("brand with brandName:"+branName+"not found"));
        brandRepo.delete(brand);
        return new SimpleResponse("brand deleted", HttpStatus.OK);
    }

    @Override
    public BrandResponse updateBrand(BrandRequest2 request) {
        Brand brand = brandRepo.getBrandByBrandName(request.oldBrandName()).orElseThrow(() -> new NotFoundException("brand with brandName:"+request.oldBrandName()+"not found"));
        brandRepo.updateBrandByBrandNameAndAndImage(request.brandName(),request.image(),brand.getId());
        return BrandResponse
                .builder()
                .brandName(request.brandName())
                .image(request.image())
                .build();
    }
}
