package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    //Write
    public void write(Board board, MultipartFile file) throws Exception {
        String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files";
        UUID uuid= UUID.randomUUID();
        String fileName = uuid+"_"+file.getOriginalFilename();
        File saveFile = new File(projectPath,fileName);
        file.transferTo(saveFile);
        board.setFilename(fileName);
        board.setFilepath("/files/"+fileName);
        boardRepository.save(board);
    }

    //List
    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }
    //Search
    public Page<Board> boardSearchlist(String searchKeyword, Pageable pageable) {
        return boardRepository.findByTitleContaining(searchKeyword,pageable);
    }
    //Load
    public Board boardView(Integer id) {
        return boardRepository.findById(id).get();
    }

    //Delete
    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);
    }
}
