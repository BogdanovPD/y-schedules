package org.why.studio.schedules.services;

import org.why.studio.schedules.dto.ConsultationRequestInput;
import org.why.studio.schedules.dto.ConsultationRequestOutputSpec;
import org.why.studio.schedules.dto.ConsultationRequestOutputUser;

import java.util.List;

public interface ConsultationRequestService {

    void save(ConsultationRequestInput consultationRequest);
    List<ConsultationRequestOutputSpec> getSpecialistConsultationRequests(String specId);
    List<ConsultationRequestOutputUser> getUserConsultationRequests(String userId);
    void approveRequest(String requestId);
    void rejectRequest(String requestId);
    void declineRequest(String requestId);
    void deleteOldRequests();
}
