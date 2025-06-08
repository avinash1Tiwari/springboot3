package com.avinash.SequirityApp.SequirityApp.services;

import com.avinash.SequirityApp.SequirityApp.entities.Session;
import com.avinash.SequirityApp.SequirityApp.entities.User;
import com.avinash.SequirityApp.SequirityApp.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {


    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT = 2;

    public void generateNewSession(User user,String refreshToken)
    {
        List<Session> userSessions = sessionRepository.findByUser(user);

        if(userSessions.size() == SESSION_LIMIT)
        {
            userSessions.sort(Comparator.comparing(Session::getLastUsedAt));

            Session leastRecentlyUsedSession = userSessions.getFirst();
            sessionRepository.delete(leastRecentlyUsedSession);

        }

        Session newSession = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();                             //// lastUsedAt and Id are auto generated

        sessionRepository.save(newSession);
    }


    public void validateToken(String refreshToken)
    {
        Session session =  sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new SessionAuthenticationException("session not found for refreshToken : " + refreshToken));

        session.setLastUsedAt(LocalDateTime.now());

        sessionRepository.save(session);
    }

}
