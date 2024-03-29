package dao;

import dao.interfaces.Dao;
import entity.Payment;
import entity.Rental;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PaymentDao implements Dao<Payment, Long> {

    private final GenericDao<Payment, Long> genericDao;

    public PaymentDao(GenericDao<Payment, Long> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return genericDao.findById(id);
    }

    @Override
    public List<Payment> findByIds(Set<Long> ids) {
        return genericDao.findByIds(ids);
    }

    @Override
    public List<Payment> findAll() {
        return genericDao.findAll();
    }

    @Override
    public void save(Payment entity) {
        genericDao.save(entity);
    }

    @Override
    public Payment update(Payment entity) {
        return genericDao.update(entity);
    }

    @Override
    public void delete(Payment entity) {
        genericDao.delete(entity);
    }

    public void acceptPaymentForRental(Rental rental) {

            var rentalCost = rental.getInventory().getFilm().getRentalInfo().getRentalRate();

            Payment payment = Payment.builder()
                    .amount(rentalCost)
                    .customer(rental.getCustomer())
                    .manager(rental.getStaffManager())
                    .build();

            payment.addRental(rental);

            genericDao.save(payment);

    }
}
