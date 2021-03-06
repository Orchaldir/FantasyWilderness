package jfw.util.ecs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ComponentMapTest {

	private static final int ID0 = 0;
	private static final int ID4 = 4;
	private static final int ID7 = 7;

	private static final int COMPONENT0 = 99;
	private static final int COMPONENT4 = 63;
	private static final int COMPONENT7 = 6;

	private ComponentMap<Integer> componentMap;

	@BeforeEach
	void setUp() {
		componentMap = new ComponentMap<>(Map.of(ID0, COMPONENT0, ID4, COMPONENT4, ID7, COMPONENT7));
	}

	@Test
	void testGetWithUnknownComponent() {
		assertThrows(NullPointerException.class, () -> componentMap.get(2), "No component for entity 2!");
	}

	@Test
	void testGet() {
		assertThat(componentMap.get(ID0)).isEqualTo(COMPONENT0);
		assertThat(componentMap.get(ID4)).isEqualTo(COMPONENT4);
		assertThat(componentMap.get(ID7)).isEqualTo(COMPONENT7);
	}

	@Test
	void testGetOptionalWithUnknownComponent() {
		assertThat(componentMap.getOptional(2)).isEqualTo(Optional.empty());
	}

	@Test
	void testGetOptional() {
		assertThat(componentMap.getOptional(ID0)).isEqualTo(Optional.of(COMPONENT0));
		assertThat(componentMap.getOptional(ID4)).isEqualTo(Optional.of(COMPONENT4));
		assertThat(componentMap.getOptional(ID7)).isEqualTo(Optional.of(COMPONENT7));
	}

	@Test
	void testGetAll() {
		assertThat(componentMap.getAll()).containsExactlyInAnyOrder(COMPONENT0, COMPONENT4, COMPONENT7);
	}

	@Test
	void testGetIds() {
		assertThat(componentMap.getIds()).containsExactlyInAnyOrder(ID0, ID4, ID7);
	}

	@Test
	void testVisit() {
		Visitor<Integer> visitor = mock(Visitor.class);

		componentMap.visit(visitor);

		verify(visitor).visit(ID0, COMPONENT0);
		verify(visitor).visit(ID4, COMPONENT4);
		verify(visitor).visit(ID7, COMPONENT7);
		verifyNoMoreInteractions(visitor);
	}

	@Test
	void testUpdateComponent() {
		ComponentStorage<Integer> newStorage = componentMap.updateComponent(ID4, -9);

		testGetOptional();

		assertThat(newStorage.getOptional(ID0)).isEqualTo(Optional.of(COMPONENT0));
		assertThat(newStorage.getOptional(ID4)).isEqualTo(Optional.of(-9));
		assertThat(newStorage.getOptional(ID7)).isEqualTo(Optional.of(COMPONENT7));
	}

	@Test
	void testUpdateComponentWithNull() {
		assertThatNullPointerException().isThrownBy(() -> componentMap.updateComponent(ID4, null));
	}

}