package base.Services;

import base.models.Product;
import base.models.Uom;
import base.models.UomType;
import base.repositories.UomRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UomService {
    private final UomRepository uomRepository;

    public Uom getSmallestUom(Product product) {
        List<Uom> smallerUomsThenReference = uomRepository.findAllByTypeAndCategoryName(UomType.SMALLER, product.getUom().getCategory().getName());
        Uom referenceUom = uomRepository.findByTypeAndCategoryName(UomType.REFERENCE, product.getUom().getCategory().getName());
        if (smallerUomsThenReference.isEmpty()) {
            return referenceUom;
        }
        Uom result=referenceUom;
        for(Uom uom : smallerUomsThenReference) {
            if(uom.getRatioToReference().compareTo(result.getRatioToReference())<0)
                result=uom;
        }
        return result;
    }
}
