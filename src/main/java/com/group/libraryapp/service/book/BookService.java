package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    //private final BookRepository bookRepository = new BookMemoryRepository();
    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final UserLoanHistoryRepository userLoanHistoryRepository;

    public BookService(BookRepository bookRepository,
                       UserLoanHistoryRepository userLoanHistoryRepository,
                       UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveBook(BookCreateRequest request) {
        bookRepository.save(new Book(request.getName()));
    }



    //2. 대출기록 정보 확인 후 대출중인지 확인
    //3. 대충중이라면 예외 발생
    @Transactional
    public void loanBook(BookLoanRequest request) {
        //1. 책 정보를 가져옴
        Book book = bookRepository.findByName(request.getBookName())
                .orElseThrow(IllegalArgumentException::new);

        //2. 대출기록 정보 확인 후 대출중인지 확인
        if (userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)) {
            throw new IllegalArgumentException("현재 대출되어 있는 책입니다.");
        }

        //유저 정보를 가져온다
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);


        user.loanBook(request.getBookName());
        //유저 정보와 책 정보를 기반으로 UserLoanHistoryRepository에 저장
        //userLoanHistoryRepository.save(new UserLoanHistory(user, book.getName(), false));
    }


    @Transactional
    public void returnBook(BookReturnRequest request) {
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);

        user.returnBook(request.getBookName());
        /*UserLoanHistory history = userLoanHistoryRepository.findByUserIdAndBookName(user.getId(), request.getBookName())
                .orElseThrow(IllegalArgumentException::new);

        history.doReturn();*/


        //userLoanHistoryRepository.save(history); <- 트랜잭션 작업내의 영속성 컨텍스트로 인해 별도 저장작업을 안해줘도 된다
    }
}
