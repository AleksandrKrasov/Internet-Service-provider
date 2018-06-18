package ua.khpi.krasov.controller.commands;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import ua.khpi.krasov.controller.commands.admin.AdminCommand;
import ua.khpi.krasov.controller.commands.admin.ClientListCommand;
import ua.khpi.krasov.controller.commands.client.BillRefillCommand;
import ua.khpi.krasov.controller.commands.client.ClientCommand;
import ua.khpi.krasov.controller.commands.client.ClientOrderCommand;
import ua.khpi.krasov.controller.commands.client.ClientServiceCommand;
import ua.khpi.krasov.controller.commands.client.ClientTariffCommand;


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
		commands.put("client", new ClientCommand());
		commands.put("settings", new SettingsCommand());
		commands.put("admin", new AdminCommand());
		commands.put("clientList", new ClientListCommand());
		
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
