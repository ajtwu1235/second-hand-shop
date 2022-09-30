package com.capstone.skone.board.application;

import com.capstone.skone.board.domain.Board;
import com.capstone.skone.board.dto.request.CreateBoardRequest;
import com.capstone.skone.board.dto.request.UpdateBoardRequest;
import com.capstone.skone.board.infrastructure.BoardRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
   private final BoardRepository boardRepository;

   public Optional<Board> findByBoard(Long id){
      return boardRepository.findById(id);
   }

   @Transactional
   public void createBoard(CreateBoardRequest createBoardRequest){
      boardRepository.save(Board.builder()
            .nickname(createBoardRequest.getNickname())
            .title(createBoardRequest.getTitle())
            .content(createBoardRequest.getContent())
            .price(createBoardRequest.getPrice())
            .fileId(createBoardRequest.getFileId())
            .build()
      );
   }

   public List<Board> findAllBoard(){
      return boardRepository.findAll();
   }

   @Transactional
   public void updateBoard(Long id, UpdateBoardRequest updateBoardRequest){
      Board board = boardRepository.findById(id).orElseThrow(()-> {
         throw new NullPointerException();
      });
      board.update(updateBoardRequest.getTitle(), updateBoardRequest.getContent(), updateBoardRequest.getPrice());
   }

   public void deleteBoard(Long id){
      boardRepository.deleteById(id);
   }

   /*paging*/
   @Transactional(readOnly = true)
   public Page<Board> pageList(Pageable pageable){
      return boardRepository.findAll(pageable);
   }

   /*title searching*/
   @Transactional
   public List<Board> keywordSearch(String title){
      return boardRepository.findByTitleContaining(title);
   }
}
