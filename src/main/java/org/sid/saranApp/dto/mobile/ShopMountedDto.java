package org.sid.saranApp.dto.mobile;

public class ShopMountedDto {
    private BoutiqueMobileDto boutiqueMobileDto;
    private StatistiqueDto  statistiqueDto;

    public BoutiqueMobileDto getBoutiqueMobileDto() {
        return boutiqueMobileDto;
    }

    public void setBoutiqueMobileDto(BoutiqueMobileDto boutiqueMobileDto) {
        this.boutiqueMobileDto = boutiqueMobileDto;
    }

    public StatistiqueDto getStatistiqueDto() {
        return statistiqueDto;
    }

    public void setStatistiqueDto(StatistiqueDto statistiqueDto) {
        this.statistiqueDto = statistiqueDto;
    }
}
