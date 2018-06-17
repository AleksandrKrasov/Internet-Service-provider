package ua.khpi.krasov.controller.commands;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;


public class CommandContainer {
	
private static final Logger log = Logger.getLogger(CommandContainer.class);
	
	private static Map<String, Command> commands = new TreeMap<String, Command>();
	
	static {
		// common commands
		commands.put("registration", new RegistrationCommand());
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("billRefill", new BillRefillCommand());
		commands.put("clientServices", new ClientServiceCommand());
		commands.put("clientTariffs", new ClientTariffCommand());
		commands.put("clientOrders", new ClientOrderCommand());
		
		log.debug("Command container was successfully initialized");
		log.trace("Number of commands --> " + commands.size());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName
	 *            Name of the command.
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			log.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand"); 
		}
		return commands.get(commandName);
	}
	
}
