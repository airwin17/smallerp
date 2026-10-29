package main.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import main.models.Product;
import main.models.Uom;
import main.models.UomType;
import main.repositories.UomRepository;

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
