<!-- Widget to show the player lobby if player is logged in, otherwise just the size. -->
<div class="player-list">
    <#if currentUser??>
    <form action="./postgame" method="POST">
        <#list playerLobby?keys as key>
            <#if key != currentUser.name>
                <input type="submit" name="key" value=${key}>
            </#if>
        </#list>
    </form>
    <#else>
        ${playerLobby?size} players in the lobby.
    </#if>
</div>