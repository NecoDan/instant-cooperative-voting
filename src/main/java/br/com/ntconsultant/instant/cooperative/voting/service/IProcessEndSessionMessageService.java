package br.com.ntconsultant.instant.cooperative.voting.service;

import br.com.ntconsultant.instant.cooperative.voting.exceptions.ProcessEndSessionException;

public interface IProcessEndSessionMessageService {

   void executeProcessMessages() throws ProcessEndSessionException;

}
