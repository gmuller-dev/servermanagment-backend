package fr.mullerguillaume.server.service.implementation;

import fr.mullerguillaume.server.enumeration.Status;
import fr.mullerguillaume.server.model.Server;
import fr.mullerguillaume.server.repo.ServerRepo;
import fr.mullerguillaume.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImplmentation implements ServerService {
    private final ServerRepo serverRepo;

    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}",server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }

    private String setServerImageUrl() {
        String[] images={"server1.png","server2.png","server3.png","server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/"+images[new Random().nextInt(4)]).toUriString();
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all server ");
        return serverRepo.findAll(Pageable.ofSize(limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id {}", id);
        return serverRepo.getById(id);
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: {}",server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by id {}", id);
        serverRepo.deleteById(id);
        return true;
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server ip : {}",ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);
        InetAddress address= InetAddress.getByName(server.getIpAddress());
        server.setStatus(address.isReachable(10000)? Status.SERVER_UP:Status.SERVER_DOWN);
        return serverRepo.save(server);
    }
}
