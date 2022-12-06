package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Hello " + name + "!";
    }
    
    @RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	
    	usuarioRepository.save(usuario);
    	
    	return "Olá Mundo " + nome;
    }
    
    @GetMapping(value = "listatodos")
    @ResponseBody
    public ResponseEntity<List<Usuario>> listaUsuarios(){
    	
    	List<Usuario> usuarios = usuarioRepository.findAll();
    	
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    	
    }
    
    @PostMapping(value = "salvar")
    @ResponseBody
    public ResponseEntity<Usuario> salvar (@RequestBody Usuario usuario){
    	
    	Usuario user = usuarioRepository.save(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }
    
    @PutMapping(value = "atualizar")
    @ResponseBody
    public ResponseEntity<?> atualizar (@RequestBody Usuario usuario){
    	
    	if (usuario.getId() == null) {
			return new ResponseEntity<String>("ID não foi informado!", HttpStatus.OK);
		}
    	
    	Usuario user = usuarioRepository.saveAndFlush(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
    
    @DeleteMapping(value = "deletar")
    @ResponseBody
    public ResponseEntity<String> deletar (@RequestParam Long id){
    	
    	usuarioRepository.deleteById(id);
    	
    	return new ResponseEntity<String>("Usuario deletado com sucesso!", HttpStatus.OK);
    }
    
    @GetMapping(value = "buscarId")
    @ResponseBody
    public ResponseEntity<Usuario> buscar (@RequestParam(name = "id") Long id){
    	
    	Usuario usuario = usuarioRepository.findById(id).get();
    	
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }
    
    @GetMapping(value = "buscarNome")
    @ResponseBody
    public ResponseEntity<List<Usuario>> buscarNome (@RequestParam(name = "name") String name){
    	
    	List<Usuario> usuarios = usuarioRepository.buscarNome(name.trim().toUpperCase());
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    }
}
