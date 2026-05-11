package no.telenor.ai.interview.customer;

public record AddressDto(
        Long id,
        AddressType type,
        String streetLine1,
        String streetLine2,
        String postalCode,
        String city,
        String countryCode,
        boolean primaryAddress
) {
}
