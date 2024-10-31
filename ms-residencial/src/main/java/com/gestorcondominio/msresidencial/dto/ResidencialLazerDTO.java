package com.gestorcondominio.msresidencial.dto;

public class ResidencialLazerDTO {

    private Long residencialId;
    private Long lazerId;

    public ResidencialLazerDTO(Long residencialId, Long lazerId) {
        this.residencialId = residencialId;
        this.lazerId = lazerId;
    }

    public Long getResidencialId() {
        return residencialId;
    }

    public void setResidencialId(Long residencialId) {
        this.residencialId = residencialId;
    }

    public Long getLazerId() {
        return lazerId;
    }

    public void setLazerId(Long lazerId) {
        this.lazerId = lazerId;
    }
}
