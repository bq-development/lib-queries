package com.bq.oss.lib.queries.request;

import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * @author Francisco Sanchez
 * 
 */
public class Sort {
	public static enum Direction {
		ASC,
		DESC
	}

	private Direction direction;
	private String field;


	public Sort(String direction, String field) {
		parseSortDirection(direction);
		this.field = field;
	}

	private void parseSortDirection(String directionString) {
		this.direction = Direction.valueOf(directionString.toUpperCase());
		if (this.direction == null) {
			throw new IllegalArgumentException();
		}
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Sort)) {
			return false;
		}
		Sort that = (Sort) obj;
		return Objects.equals(this.direction, that.getDirection()) && Objects.equals(this.field, that.getField());
	}

	@Override
	public int hashCode() {
		int result = direction != null ? direction.hashCode() : 0;
		result = 31 * result + (field != null ? field.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "{\"" + this.getField() + "\":\"" + this.getDirection().name().toLowerCase() + "\"}";
	}

}
