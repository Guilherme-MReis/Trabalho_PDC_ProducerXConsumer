/*
Autores: Guilherme Martins Reis
         Hayron Piffer
         Raniery Bertolino
*/

public class TrabalhoThread
{
    public static void main(String[] args) throws InterruptedException
    {
            // Objeto da classe que possui as funções consumidoras e a produtra 
            final Conta pc = new Conta(); 

            // Definição da Thread APatrocinadora
            Thread APatrocinadora = new Thread(new Runnable() { 
		@Override
		public void run()
		{
                    try {
                        pc.deposita();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
		}
            });

            // Definição da Thread AGastadora
            Thread AGastadora = new Thread(new Runnable() {
		@Override
		public void run() 
		{
                    try { 
			pc.consumeAGastadora();
                        //Thread.sleep(3000);
                    } 
                    catch (InterruptedException e) {
			e.printStackTrace();
                    }
		}
            });
            
            // Definição da Thread AEsperta
            Thread AEsperta = new Thread(new Runnable() {
		@Override
		public void run() 
		{
                    try { 
			pc.consumeAEsperta();
                        //Thread.sleep(6000);
                    } 
                    catch (InterruptedException e) {
			e.printStackTrace(); 
                    }
		}
            });
            
            // Definição da Thread AEconomica
            Thread AEconomica = new Thread(new Runnable() {
		@Override
		public void run() 
		{
                    try { 
			pc.consumeAEconomica();
                        //Thread.sleep(12000);
                    } 
                    catch (InterruptedException e) {
			e.printStackTrace();
                    }
		}
            });
            
            // Inicia todas as Threads
            AGastadora.start();
            AEsperta.start();
            AEconomica.start();
            APatrocinadora.start();

    }

    /* 
    *  Nesta classe, temos as definições das funções consumidoras e produtora
    */
    public static class Conta 
    {
	int saldo = 1000; //Saldo compartilhado dentre as Threads
        int saquesAG = 0; //Varíavel responsável por guardar a quantidade de saques feitos por AGastadora
        int saquesES = 0; //Varíavel responsável por guardar a quantidade de saques feitos por AEsperta
        int saquesEC = 0; //Varíavel responsável por guardar a quantidade de saques feitos por AEconomica

	// Função chamada pela Thread produtora APatrocinadora
	public void deposita() throws InterruptedException
	{
            while (true)
            {
                synchronized(this){
                    // Thread produtora fica esperando enquanto o saldo não estiver zerado
                    if (saldo > 0){
                        wait();
                        
                    //Assim que o saldo zerar, a Thread APatrocinadora deposita
                    //100 no saldo e imprime a quantidade de saques realizados
                    //por cada Thread consumidora e logo em seguida zera as suas
                    //respectivas variáveis para recomeçar os saques
                    }if(saldo == 0){
                        saldo = 100;
                        
                        System.out.println("Saldo zerou!\nValor depositado:" + saldo);
                        System.out.println("Quantidade de saques da Gastadora: " + saquesAG);
                        System.out.println("Quantidade de saques da Esperta: " + saquesES);
                        System.out.println("Quantidade de saques da Economica: " + saquesEC);
                        System.out.println("\n");
                        
                        saquesAG = 0;
                        saquesES = 0;
                        saquesEC = 0;
                        
                        //Função para acordar todas as Threads em espera
                        notifyAll();
                    }
		}
            }
	}

	// Função chamada pela Thread consumidora AGastadora
        // Esta Thread saca 10 reais a cada 3 segundos
	public void consumeAGastadora() throws InterruptedException
	{
            int carteiraAG = 0; //Variável que guarda o total de saques realizados por AGastadora
            
            while (true)
            {
                synchronized(this)
		{
                    // Thread consumidora fica em espera quando o saldo for
                    // zerado ou o saldo for menor que o seu valor de saque
                    if (saldo == 0 || saldo < 10){
                        wait();
                    }else{
                        // Subtrai 10 reais do saldo e adiciona a sua carteira.
                        saldo = saldo - 10;
                        carteiraAG = carteiraAG + 10;
                        saquesAG++;

                        System.out.println("AGastadora tirou 10. Carteira atual " +carteiraAG);
                        System.out.println("Saldo atual " +saldo);
                        
                        //Função para acordar todas as Threads em espera
                        notifyAll();

                        // Função de espera de 3 segundos antes de iniciar um novo saque
                        wait(3000);
                    }
		}
            }
	}
        
        // Função chamada pela Thread consumidora AEsperta
        // Esta Thread saca 50 reais a cada 6 segundos
	public void consumeAEsperta() throws InterruptedException
	{
            int carteiraAEs = 0; //Variável que guarda o total de saques realizados por AEsperta
            
            while (true)
            {
                synchronized(this)
		{
                    // Thread consumidora fica em espera quando o saldo for
                    // zerado ou o saldo for menor que o seu valor de saque
                    if (saldo == 0 || saldo < 50){
                        wait();
                    }else{
                        // Subtrai 50 reais do saldo e adiciona a sua carteira.
                        saldo = saldo - 50;
                        carteiraAEs = carteiraAEs + 50;
                        saquesES++;
                        System.out.println("AEsperta tirou 50. Carteira atual " + carteiraAEs);
                        System.out.println("Saldo atual " +saldo);
                        
                        //Função para acordar todas as Threads em espera
                        notifyAll();

                        // Função de espera de 6 segundos antes de iniciar um novo saque 
                        wait(6000);
                    }
		}
            }
	}
        
        // Função chamada pela Thread consumidora AEconomica
        // Esta Thread saca 5 reais a cada 12 segundos
	public void consumeAEconomica() throws InterruptedException
	{
            int carteiraAEc = 0; //Variável que guarda o total de saques realizados por AEconomica
            
            while (true)
            {
                synchronized(this)
		{
                    // Thread consumidora fica em espera quando o saldo for
                    // zerado ou o saldo for menor que o seu valor de saque
                    if (saldo == 0 || saldo < 5){
                        wait();
                    }else{
                        // Subtrai 5 reais do saldo e adiciona a sua carteira.
                        saldo = saldo - 5;
                        carteiraAEc = carteiraAEc + 5;
                        saquesEC++;
                        System.out.println("AEconomica tirou 5. Carteira atual " + carteiraAEc);
                        System.out.println("Saldo atual " +saldo);
                        
                        //Função para acordar todas as Threads em espera
                        notifyAll();

                        // Função de espera de 12 segundos antes de iniciar um novo saque
                        wait(12000);
                    }
		}
            }
	}
    }
}