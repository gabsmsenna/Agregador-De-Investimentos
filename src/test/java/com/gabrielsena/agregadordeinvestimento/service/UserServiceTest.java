package com.gabrielsena.agregadordeinvestimento.service;

import com.gabrielsena.agregadordeinvestimento.DTOS.CreateUserDTO;
import com.gabrielsena.agregadordeinvestimento.entity.User;
import com.gabrielsena.agregadordeinvestimento.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser {

        @Test
        @DisplayName(("Deve criar um usuário com sucesso"))
        void deveCriarUmNovoUsuario() {

            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "12345678",
                    Instant.now(),
                    null
            );
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            var input = new CreateUserDTO(
                    "username",
                    "email@email.com",
                    "123"
            );

            // Act
            var output = userService.createUser(input);

            // Assert
            assertNotNull(output);

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());

        }

        @Test
        @DisplayName("Deveria estourar uma exceção quando um erro ocorrer")
        void deveEstourarUmaExcecaoQuandoUmErroOcorrer() {
            //Arrange
            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDTO(
                    "username",
                    "email@email.com",
                    "123"
            );

            // Act & Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }

    }

    @Nested
    class getUserById {

        @Test
        @DisplayName("Deve retornar um usário pelo id com sucesso quando optional estiver presente")
        void deveriaRetornarUsuarioPeloIdQuandoOptionalEstiverPresente() {


            // Arrange

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "12345678",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());

            // Act

            var output = userService.getUserById(user.getUserId().toString());

            //Assert
            assertTrue(output.isPresent());
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue() );
        }

        @Test
        @DisplayName("Deve retornar um usário pelo id com sucesso quando optional estiver vazio")
        void deveriaRetornarUsuarioPeloIdQuandoOptionalEstiverVazio() {


            // Arrange

            var userId = UUID.randomUUID();

            doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());

            // Act

            var output = userService.getUserById(userId.toString());

            //Assert
            assertTrue(output.isEmpty());
            assertEquals(userId, uuidArgumentCaptor.getValue());
        }

    }

    @Nested
    class listUsers {

        @Test
        @DisplayName("Deve retornar todos os usuários com sucesso")
        void deveRetornarTodosOsUsuariosComSucesso() {

            //Arrange

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "12345678",
                    Instant.now(),
                    null
            );
            var userList = List.of(user);
            doReturn(List.of(user))
                    .when(userRepository)
                    .findAll();

            // Act

            var output = userService.listUsers();

            //Assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Deve deletar pelo id com sucesso quando o usuario existe")
        void deveDeletarPeloIdComSucessoQuandoUsuarioExiste() {

            // Arrange

            doReturn(true)
                    .when(userRepository)
                            .existsById(uuidArgumentCaptor.capture());

            doNothing()
                    .when(userRepository)
                    .deleteById(uuidArgumentCaptor.capture());

            var userid = UUID.randomUUID();

            // Act

            userService.deleteUserById(userid.toString());

            // Assert

            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userid, idList.get(0));
            assertEquals(userid, idList.get(1));

            verify(userRepository, times(1)).existsById(idList.get(0));
            verify(userRepository, times(1)).deleteById(idList.get(0));

        }

        @Test
        @DisplayName("Não deve deletar usuário quando usuário não existir")
        void naoDeveDeletarUsuarioQuandoUsuarioNaoExistir() {

            // Arrange

            doReturn(false)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            var userid = UUID.randomUUID();

            // Act

            userService.deleteUserById(userid.toString());

            // Assert
            assertEquals(userid,uuidArgumentCaptor.getValue());

            verify(userRepository, times(1))
                    .existsById(uuidArgumentCaptor.getValue());

            verify(userRepository, times(0)).deleteById(any());

        }
    }

    @Nested
    class updateUserById {

        @Test
        @DisplayName("Deve atualizar o usuário pelo id quando usuario existir e username e senha estiver preenchido")
        void deveriaAtualizarUsuarioPeloIdQuandoUsernameAndPasswordEstiverPreenchido () {

            // Arrange

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "12345678",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            // Act

            var output = userService.getUserById(user.getUserId().toString());

            //Assert
            assertTrue(output.isPresent());
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue() );
        }

    }

}