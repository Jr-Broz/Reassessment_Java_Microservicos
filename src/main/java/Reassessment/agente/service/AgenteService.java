package Reassessment.agente.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Reassessment.DTO.Agente.AgenteRequestDTO;
import Reassessment.DTO.Agente.AgenteResponseDTO;
import Reassessment.agente.Agente;
import Reassessment.agente.repository.AgenteRepository;
import Reassessment.common.exception.ConflitoRegraNegociosException;
import Reassessment.common.exception.DadosInvalidosException;
import Reassessment.common.exception.RecursoNaoEncontradoException;
import Reassessment.enums.Especialidade;
import Reassessment.enums.statusAgente;
import Reassessment.missao.repository.MissaoRepository;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class AgenteService {
    
    private final AgenteRepository agenteRepository;
    private final MissaoRepository missaoRepository;


    private Agente buscarEntidade(Long id) {

        return agenteRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Agente com ID: " + id + "nao foi achado"));
    }


    // voltar aq

    // @Transactional(readOnly = true)
    // public List<AgenteResponseDTO> listar(statusAgente statusAgente, Especialidade especialidade){

    //     List<Agente> agentes;

    //     if(statusAgente != null ) {

    //         agentes = agenteRepository.findByStatusDoAgente(statusAgente);
    //     }
    //     else if(especialidade != null){
    //       agentes = agenteRepository.findByEspecialidade(especialidade);
         
    //     }
    //     else{
    //         agenteRepository.findAll();
    //     }

    //     List<Agente> agente;
      
    //     return agente.stream().map(AgenteResponseDTO :: new).toList();
    // }


    @Transactional(readOnly = true)
    public AgenteResponseDTO buscarPorId(Long id){

     Agente agente = buscarEntidade(id);

     return new AgenteResponseDTO(agente);        
        
    } 



 @Transactional
 public AgenteResponseDTO criar(AgenteRequestDTO agenteRequestDTO){

    if (agenteRepository.existsByMatricula(agenteRequestDTO.getMatricula())) {

        throw new ConflitoRegraNegociosException("Ja existe um agente com essa matircula");
    }

    Agente agente = new Agente();

    agente.setNome(agenteRequestDTO.getNome());
    agente.setMatricula(agenteRequestDTO.getMatricula());
    agente.setEspecialidade(agenteRequestDTO.getEspecialidade());

    Agente salvo = agenteRepository.save(agente);

    return new AgenteResponseDTO(salvo);
 }

  @Transactional 
  public AgenteResponseDTO atualizar(Long id, AgenteRequestDTO requestDTO) {

    Agente agente = buscarEntidade(id); 

    if(!requestDTO.getMatricula().equals(agente.getMatricula()) && agenteRepository.existsByMatricula(requestDTO.getMatricula())) {

        throw new  DadosInvalidosException("Matricula enviada e diferente da atual. ");
    }

    agente.setNome(requestDTO.getNome());    
    agente.setMatricula(requestDTO.getMatricula());
    
    agente.setEspecialidade(requestDTO.getEspecialidade());

   return new AgenteResponseDTO(agente);
  }


























}