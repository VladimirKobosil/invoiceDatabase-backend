package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    /**
     * Metoda pro převod objektu faktury z entity na DTO.
     *
     * @param source zdrojová faktura v podobě entity
     * @return faktura v podobě DTO
     */
    InvoiceDTO toDTO(InvoiceEntity source);

    /**
     * Metoda pro převod objektu faktury z DTO na entitu.
     *
     * @param source zdrojová faktura v podobě DTO
     * @return faktura v podobě entity
     */
    InvoiceEntity toEntity(InvoiceDTO source);

    /**
     * Metoda pro aktualizaci existující entity faktury na základě dat z DTO.
     *
     * @param source zdrojová faktura v podobě DTO
     * @param target cílová faktura v podobě entity
     * @return aktualizovaná faktura v podobě entity
     */
    @Mapping(target = "buyer", ignore = true) // Ignorování mapování kupujícího
    @Mapping(target = "seller", ignore = true)// Ignorování mapování prodávajícího
    InvoiceEntity updateEntity(InvoiceDTO source, @MappingTarget InvoiceEntity target);
}
