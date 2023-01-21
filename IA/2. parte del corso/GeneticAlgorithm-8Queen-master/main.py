# Application of genetic algorithm on 8queen problem

from geneticAlgorithm import initializeRandomPopulation, crossover, mutation, sortPopulation, countThreat
from queenBoard import printBoard

populationSize = 10 # Number of chromosomes
chromosomeSize = 30 # Number of queens (genes in each chromosome). In ogni cromosoma, la posizione del gene indica la colonna, 
                   # il valore indica la riga. NB: posso indicare anche un numero diverso e avere una scacchiera più grande
                   # o più piccola
iterations = 10000 # Number of iterations that genetic algorithm runs
mutationCount = 5 # Number of mutations in each iteration
crossoverCount = 5 # Number of crossovers in each iteration

# population è una lista di array. Ogni array è un cromosoma
population = initializeRandomPopulation(populationSize, chromosomeSize) # Initialize population (chromosomes)

# mostra la configurazione iniziale delle regine sulla scacchiera per un generico cromosoma (in questo caso 
# il cromosoma 5)
printBoard(population[5], chromosomeSize)
wait = input("Press Enter to continue.")

# Ciclo principale dell'algoritmo
for iteration in range(0, iterations): # run from iteration 0 to total number of iterations defined above
    print('Generazione: ' + str(iteration))
    population = crossover(population, crossoverCount) # Apply genetic algorithm cross over, over the population
    population = mutation(population, mutationCount) # Apply genetic algorithm mutation, over the population
    population = sortPopulation(population) # sort the population by their value for selection part
    # Prende i migliori della graduatoria
    # Un altro modo per scegliere la nuova generazione è quello della selezione
    # attraverso il meccanismo della roulette
    population = population[:populationSize] # select top chromosomes of population
    #print('Minacce: ' + str(countThreat (population[0])))
    #print('Popolazione: ' + str(len(population)))
    # L'algoritmo si ferma appena trova un cromosoma che ha un numero di minacce nullo
    # Siccome, per come è fatto l'algoritmo, il primo cromosoma della lista è il migliore,
    # controllo se questo ha il numero delle minacce nullo
    if countThreat(population[0]) == 0:        
        break

print("position vector of queens: " + str(population[0]))
print("number of queens threats: " + str(countThreat(population[0])))

printBoard(population[0], chromosomeSize)


