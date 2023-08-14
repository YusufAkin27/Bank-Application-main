package com.example.finalproject.transfer.repository;


import com.example.finalproject.transfer.entity.Transfer;
import com.example.finalproject.transfer.exception.NotFoundTransferException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    default Transfer findByIdWithException(long id) throws NotFoundTransferException {
        return findById(id)
                .orElseThrow(() -> new NotFoundTransferException());
    }
}
